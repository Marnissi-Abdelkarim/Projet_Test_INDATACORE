import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/security/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  formGroupLogin: FormGroup;
  errMsg: string;

  constructor(private fb: FormBuilder, private auth: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
    this.form();
  }

  form(){
    this.formGroupLogin = this.fb.group({
      email: new FormControl('', [
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(12)
      ]),
    });
  }

  get email(){
    return this.formGroupLogin.get('email');
  }

  get password(){
    return this.formGroupLogin.get('password');
  }


  login(){
    if(this.formGroupLogin.invalid){
      this.formGroupLogin.markAllAsTouched();
    }else{
      this.auth.authenticate(this.email.value, this.password.value).subscribe({
        next: data => {
          this.router.navigate(['/']);
          console.log(this.password.value);

        },
        error: err => {
          this.errMsg = 'Email or Password is incorrect';
        }
      });
    }

  }

}
