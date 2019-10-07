import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { target } from './Contato/target';
import { Contato } from './Contato/contato';
import { isNullOrUndefined } from 'util';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  contatos:any = [];
  contato:Contato;
  constructor(private http:HttpClient){}
  ngOnInit(){
    this.contato = new Contato();
    this.carregaTela();
  }
  carregaTela(){
    return this.http.get(`${target.url()}/contato`).subscribe(result=>{
      this.contatos = result;
    },error => console.log(error));
  }
  salvar():void{
    if(isNullOrUndefined(this.contato.id)){
    this.contato.id = 0;
    this.http.post(`${target.url()}/contato`,this.contato).subscribe(result =>{
      window.location.reload();
    },error => this.carregaTela());
  }else{
    this.http.put(`${target.url()}/contato/${this.contato.id}`,this.contato).subscribe(result=>{
      this.carregaTela();
    },error => console.log(error));
  }
  }
  deletar(id:number):void{
    this.http.delete(`${target.url()}/contato/${id}`).subscribe(result=>{
      this.carregaTela();
    },error => console.log(error));
  }
  carregarColaborador(usuario:Contato){
    this.contato = usuario;
  }
}
