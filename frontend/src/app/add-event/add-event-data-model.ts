export class AddEventDataModel {

    constructor(
        public eventTitle: string = '',
        public startTime: Date = null,
        public endTime: Date = null,
        public location: string = '',
        public participants?: Array<string>,
        public additionalInfo?: string
    ) {}
}