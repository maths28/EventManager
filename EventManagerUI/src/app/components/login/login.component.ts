import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButton, MatGridList, MatGridTile],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  form: FormGroup;
  isLogged: boolean = false;
  isSubmitted: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  async onSubmit(): Promise<void>{
    if(this.form.valid){
      this.isSubmitted = false;
      this.form.disable();
      this.isLogged = await this.loginService.login(this.form.value.username, this.form.value.password);
      this.isSubmitted = true;
      if(!this.isLogged){
        this.form.enable();
      }
    }
  }



}
