export class AddEventDataModel {

    constructor(
        public title: string = '',
        public startTime: any = null,
        public endTime: any = null,
        public location: string = '',
        public participants: any = [],
        public description?: string,
    ) {}
}