export class Participant{
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  events: string;
}

export class PageParticipant {
  content: Participant[];
  totalElements: number;
}

