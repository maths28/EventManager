import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";
import moment, {Moment} from "moment";
import {environment} from "../../environments/environment";

export const startDateEventFormValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  let startDate: Moment|null = null;
  if (control.value) {
    startDate = moment.isMoment(control.value) ? control.value : moment(control.value, environment.DATE_TIME_FORMAT);
    if(startDate.isSameOrBefore(moment.now())){
      return {startDate: true};
    }
  }
  return null;
};

export const endDateEventFormValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  let startDate: Moment|null = null;
  let endDate: Moment|null = null;

  let startDateControl =  control.parent?.get('startDate');

  if (control.value && startDateControl?.value) {
    startDate = moment.isMoment(startDateControl.value) ? startDateControl.value : moment(startDateControl.value, environment.DATE_TIME_FORMAT);
    endDate = moment.isMoment(control.value) ? control.value : moment(control.value, environment.DATE_TIME_FORMAT);
    if(endDate.isSameOrBefore(startDate)){
      return {endDate: true};
    }
  }
  return null;
};
