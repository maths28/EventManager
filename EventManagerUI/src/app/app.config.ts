import {ApplicationConfig} from '@angular/core';
import {provideRouter, withComponentInputBinding, withRouterConfig} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import 'moment/locale/fr';
import {errorInterceptor} from "./interceptor/error.interceptor";
import {credentialsInterceptor} from "./interceptor/credentials.interceptor";


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes, withComponentInputBinding()),
    provideAnimationsAsync(),
    provideHttpClient(
      withInterceptors([credentialsInterceptor, errorInterceptor])
    ),
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'fr',
    }
  ]
};
