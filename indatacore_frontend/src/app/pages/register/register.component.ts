import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/security/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  formGroupRegister: FormGroup;
  constructor(private fb: FormBuilder, private auth: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
    this.formRegister();
  }

  formRegister(){
    this.formGroupRegister = this.fb.group({
      username: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
      ])
    });
  }
  get username(){
    return this.formGroupRegister.get('username');
  }
  get email(){
    return this.formGroupRegister.get('email');
  }
  get password(){
    return this.formGroupRegister.get('password');
  }

  register(){
    if(this.formGroupRegister.invalid){
      this.formGroupRegister.markAllAsTouched();
    }else{
      this.auth.createUser(this.username.value, this.email.value, this.password.value).subscribe({
        next: data => {
          this.router.navigate(['/login']);
        },
        error: err => {
          console.log(err);
        }
      });
    }
  }

}
