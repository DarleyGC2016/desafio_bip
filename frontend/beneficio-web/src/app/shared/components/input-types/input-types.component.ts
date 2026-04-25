import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'input-global',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './input-types.component.html',
  styleUrl: './input-types.component.css',
})
export class InputTypesComponent {
  @Input()
  data!: string | number | boolean;
  @Input("disabled")
  habilitado: boolean = true;
  @Input("label")
  rotulo: string = '';

}
