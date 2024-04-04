export class ErrorResponse {
  resumeErrorMessage: string;
  detailedErrorMessages?: string[];
  constructor(resumeErrorMessage: string, detailedErrorMessages?: string[]) {
    this.resumeErrorMessage = resumeErrorMessage;
    this.detailedErrorMessages = detailedErrorMessages;
  }
}
