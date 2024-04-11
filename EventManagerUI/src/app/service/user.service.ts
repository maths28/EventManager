import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {catchError, debounceTime, distinctUntilChanged, first, map, Observable, of, switchMap} from "rxjs";
import {User} from "../model/user";
import {AbstractControl, ValidationErrors} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private httpClient: HttpClient) { }

  existsByEmailValidator(control: AbstractControl): Observable<ValidationErrors | null> {
    return control.valueChanges.pipe(
      debounceTime(300), // Attend 300 ms après le dernier événement de frappe
      distinctUntilChanged(), // Continue uniquement si la valeur a changé
      switchMap(value =>
        this.httpClient.get<boolean>(`${environment.BASE_URL}user/search`, {
          params: { email: value }
        }).pipe(
          map(exists => (exists ? { uniqueEmail: true } : null)),
          catchError(() => of(null)) // Gestion des erreurs, retourne null en cas d'erreur
        )
      ),
      first()
    );
  }

  registerUser(user: User): Observable<User>{
    return this.httpClient.post<User>(`${environment.BASE_URL}user`, user);
  }

}
