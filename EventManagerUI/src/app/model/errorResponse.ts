export class ErrorResponse {
  resumeErrorMessage: string;
  otherInfoAboutError?: string;
  detailedErrorMessages?: string[];
  constructor(resumeErrorMessage: string, otherInfoOrDetails?: string|string[]) {
    this.resumeErrorMessage = resumeErrorMessage;
    if(typeof otherInfoOrDetails == 'string'){
      this.otherInfoAboutError = otherInfoOrDetails;
    } else {
      this.detailedErrorMessages = otherInfoOrDetails;
    }
  }
}
