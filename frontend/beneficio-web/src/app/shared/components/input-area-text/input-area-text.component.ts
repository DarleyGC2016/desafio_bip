import { Component, Input, model } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'input-area-text',
  imports: [MatInputModule, MatFormFieldModule, FormsModule],
  templateUrl: './input-area-text.component.html',
  styleUrl: './input-area-text.component.css',
})
export class InputAreaTextComponent {
  data = model<string>('');
  @Input("disabled")
  habilitado: boolean = true;
  @Input("label")
  rotulo: string = '';
}
