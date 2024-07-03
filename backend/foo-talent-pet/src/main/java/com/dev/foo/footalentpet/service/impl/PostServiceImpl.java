package com.dev.foo.footalentpet.service.impl;

import com.dev.foo.footalentpet.exception.NotFoundException;
import com.dev.foo.footalentpet.mapper.CommentDTOMapper;
import com.dev.foo.footalentpet.mapper.PostDTOMapper;
import com.dev.foo.footalentpet.model.entity.Post;
import com.dev.foo.footalentpet.model.entity.PostTag;
import com.dev.foo.footalentpet.model.request.PostRequestDTO;
import com.dev.foo.footalentpet.model.response.CommentResponseDTO;
import com.dev.foo.footalentpet.model.response.PostCommentResponseDTO;
import com.dev.foo.footalentpet.model.response.PostResponseDTO;
import com.dev.foo.footalentpet.repository.*;
import com.dev.foo.footalentpet.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostDTOMapper postDTOMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentDTOMapper commentDTOMapper;

    @Override
    public PostResponseDTO create(PostRequestDTO postDTO) {
        Post post = postDTOMapper.postResponseDtoToPost(postDTO);
        if (Objects.isNull(post.getUser())) {
            throw new NotFoundException("User not found");
        }
        Post savedPost = postRepository.save(post);

        List<PostTag> postTags = post.getPostTags().stream()
                .map(tagId -> new PostTag(savedPost, tagId.getTag()))
                .toList();

        postTagRepository.saveAll(postTags);

        savedPost.setPostTags(new HashSet<>(postTags));

        return postDTOMapper.postToPostResponseDto(savedPost);
    }

    @Override
    public PostCommentResponseDTO findById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        PostResponseDTO postResponseDTO = postDTOMapper.postToPostResponseDto(post);

        List<CommentResponseDTO> comments = commentRepository.findByPostId(id).stream()
                .map(commentDTOMapper::toDTO)
                .toList();
        return new PostCommentResponseDTO(postResponseDTO, comments);
    }

    @Override
    public List<PostResponseDTO> findAll() {
        return postRepository.findAll().stream()
                .map(postDTOMapper::postToPostResponseDto)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        postRepository.deleteById(id);
    }
}