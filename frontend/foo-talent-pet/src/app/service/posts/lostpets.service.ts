import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { Filters } from '../../interfaces/interfaces';


@Injectable({
  providedIn: 'root'
})
export class LostpetsService {

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.apiUrl;

  getPets(status:string, filters: Filters, order: boolean): Observable<any> {
    let query = `recent=${order}`;
    if (filters.animal) {
      query += `&speciesType=${filters.animal}`;
    }
    if (filters.gender) {
      query += `&gender=${filters.gender}`;
    }
    if (filters.province) {
      query += `&province=${filters.province}`;
    }
    if (filters.city) {
      query += `&city=${filters.city}`;
    }
    if (filters.locality) {
      query += `&locality=${filters.locality}`;
    }
    if (filters.date) {
      query += `&date=${filters.date}`;
    }
    if (filters.colors && filters.colors.length > 0) {
      query += `&colorIds=${filters.colors}`;
    }
    if (filters.tags && filters.tags.length > 0) {
      query += `&tagIds=${filters.tags}`;
    }
    return this.http.get<any>((`${this.apiUrl}posts/${status}?${query}`));
  }

}