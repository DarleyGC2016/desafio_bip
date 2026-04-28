import { CommonModule } from '@angular/common';
import { Component, input, Input, model } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'input-moeda-real',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    NgxMaskDirective
  ],
  templateUrl: './input-moeda-real.component.html',
  styleUrl: './input-moeda-real.component.css',
})
export class InputMoedaRealComponent {
  
  disabled = input<boolean>(true);
  label = input<string>("");
  data = model<number>();
  isAtivoMoeda = input<boolean>(false);
}
