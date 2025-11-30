
export class IdTokeVerificationError extends Error {
    constructor(message: string) {
        super(message);
        this.name = 'IdTokeVerificationError';
    }
}
