import { Component, inject } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { RouterLink } from '@angular/router';
import { MascotasadopcionComponent } from '../mascotasadopcion/mascotasadopcion.component';
import { MascotasencontradasComponent } from '../mascotasencontradas/mascotasencontradas.component';
import { MascotasperdidasComponent } from '../mascotasperdidas/mascotasperdidas.component';
import { ModalPreguntaComponent } from '../modal-pregunta/modal-pregunta.component';
import { ConfirmacionService } from '../../service/confirmacion.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-form-plublication',
  standalone: true,
  imports: [
    NavbarComponent, 
    FooterComponent, 
    RouterLink, 
    MascotasadopcionComponent, 
    MascotasencontradasComponent, 
    MascotasperdidasComponent,
    ModalPreguntaComponent,
  ],
  templateUrl: './form-plublication.component.html',
  styleUrl: './form-plublication.component.css'
})
export class FormPlublicationComponent {
  private _confirmacionService = inject(ConfirmacionService);


  public cargarConfirmacion():void{
    this._confirmacionService.mostrarConfirmacion();
  }
  
}