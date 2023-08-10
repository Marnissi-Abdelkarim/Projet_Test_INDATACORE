import { Observable, catchError, map, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl:string = "http://localhost:8080/";

  constructor(private http: HttpClient) {}


  authenticate(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}login`, { email, password }).pipe(
      map(response => {
        this.storeUserData(response.email, response.token);
        return response;
      }),
      catchError(error => {
        return error;
      })
    );
  }

  private storeUserData(email: string, token: string): void {
    sessionStorage.setItem('email', email);
    sessionStorage.setItem('token', `Bearer ${token}`);
  }


  createUser(username: string, email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}users`, { username, email, password });
  }

  getAuthenticatedUser(): string | null {
    return sessionStorage.getItem('email');
  }

  getToken(): string | null {
    return this.getAuthenticatedUser() ? sessionStorage.getItem('token') : null;
  }

  isAuthenticated(): boolean {
    return this.getToken() !== undefined;
  }

  logout() {
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('token');
  }

}
