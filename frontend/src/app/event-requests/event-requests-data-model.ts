export class EventRequestsDataModel {

    constructor(
        public eventTitle: string = '',
        public eventOwner: string = '',
        public startTime: Date = null,
        public endTime: Date = null,
        public location: string = '',
        public participants?: Array<string>,
        public additionalInfo?: string
    ) {}
}