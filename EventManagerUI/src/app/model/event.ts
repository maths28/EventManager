import {Participant} from "./participant";

export class Event{
  id: number;
  name: string;
  description: string;
  startDate: string;
  endDate: string;
  location: string;
  maxCapacity: number;
  totalParticipants: number;
}

export class PageEvent {
  content: Event[];
  totalElements: number;
}
