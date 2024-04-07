import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';

export const credentialsInterceptor: HttpInterceptorFn = (req, next) => {
  let httpHeaders = req.headers;
  httpHeaders = httpHeaders.append('X-Requested-With', 'XMLHttpRequest');

  let options: any = {
    headers: httpHeaders
  }

  if(!req.url.startsWith('/user')){
    options.withCredentials = true;
  }

  return next(
    req.clone(options)
  );
};
