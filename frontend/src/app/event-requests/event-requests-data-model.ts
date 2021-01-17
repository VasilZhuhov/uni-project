export class EventRequestsDataModel {

    constructor(
        public id: number,
        public title: string = '',
        public creatorEmail: string = '',
        public startTime: Date = null,
        public endTime: Date = null,
        public location: string = '',
        public participants?: Array<string>,
        public description?: string
    ) {}
}