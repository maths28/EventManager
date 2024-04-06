import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map, Observable} from "rxjs";
import {User} from "../model/user";
import {AbstractControl, ValidationErrors} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private httpClient: HttpClient) { }

  findByEmail(email: string): Observable<User|undefined> {
    return this.httpClient.get<User|undefined>(`${environment.BASE_URL}user/search`, {
      params: {
        email: email
      }
    })
  }

  existsByEmailValidator(control: AbstractControl): Observable<ValidationErrors|null>{
      return this.findByEmail(control.value).pipe(
        map((user: User|undefined) => user && user.id ? {uniqueEmail: true} : null)
      );
  }

  registerUser(user: User): Observable<User>{
    return this.httpClient.post<User>(`${environment.BASE_URL}user`, user);
  }

}
