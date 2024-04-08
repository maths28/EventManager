import {HttpInterceptorFn, HttpXsrfTokenExtractor} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {inject} from "@angular/core";

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
  let httpHeaders = req.headers;

  //Pour éviter l'ouverture de la popup d'authentification Basic
  httpHeaders = httpHeaders.append('X-Requested-With', 'XMLHttpRequest');

  //Envoi du token XSRF si existant et requete cohérente
  if(!["GET", "HEAD", "TRACE", "OPTIONS"].includes(req.method) &&
    !req.url.startsWith(`${environment.BASE_URL}user`)){
    const tokenExtractor: HttpXsrfTokenExtractor = inject(HttpXsrfTokenExtractor);
    const token: string|null = tokenExtractor.getToken();
    if(token){
      httpHeaders = httpHeaders.append("X-XSRF-TOKEN", token);
    }
  }

  let options: any = {
    headers: httpHeaders
  }

  //Envoi des cookies dans la requete, incluant le JSESSIONID permettant le
  //chargement de l'utilisateur connecté enregistré en session
  if(!req.url.startsWith('/user')){
    options.withCredentials = true;
  }

  return next(
    req.clone(options)
  );
};
