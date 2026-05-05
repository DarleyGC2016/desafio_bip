import { Component, OnDestroy, OnInit, signal } from '@angular/core';
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-home',
  imports: [MatIcon],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit, OnDestroy{
frases = signal<string[]>([
    "🚀 Comprometido com a excelência técnica e evolução constante .",
    "💡 Transformando desafios complexos em soluções simples e eficientes.",
    "🤝 Pronto para somar ao time da BIP – Business Integration Partners com foco e proatividade.",
    "🛠️ Especialista em construir software com códigos limpos e fácil manutenção."
  ]);

  index = signal<number>(0);
  intervalId: any;

  ngOnInit(): void {
      this.intervalId = setInterval(()=> {
        this.index.update(i => (i + 1) % this.frases().length);
      }, 5000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  
}
