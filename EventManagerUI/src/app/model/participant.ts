import {User} from "./user";

export class Participant extends User {
  firstName: string;
  lastName: string;
}

export class PageParticipant {
  content: Participant[];
  totalElements: number;
}

