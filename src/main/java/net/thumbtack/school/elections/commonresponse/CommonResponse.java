package net.thumbtack.school.elections.commonresponse;

public enum CommonResponse {
    SUCCESSFULLY_LOGGED("You have successfully logged in to the server"),
    YOU_CANDIDATE("You're a candidate now"),
    YOU_NO_CANDIDATE("You have cancelled the nomination of his candidacy"),
    YOU_lEFT_SERVER("You left the server"),
    YOU_REMOVE_SERVER("You have been removed from the server"),
    CONSENT_NOMINATION("You consent to nomination"),
    NOMINATE("You nominate yourself"),
    REMOVE_PROPOSAL("The offer was removed from the program"),
    ADD_PROPOSAL_TO_PROGRAM("Offer added to the program"),
    ADD_PROPOSAL("The proposal attached to the base"),
    RATE_PROPOSAL("You rated the offer"),
    CANCEL_RATE_PROPOSAL("You canceled the evaluation of the offer"),
    VOTED_FOR_CANDIDATE("You voted for the candidate"),
    VOTED_AGAINST_ALL("You voted against all of them");

    private String commonResponse;

    CommonResponse(String commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getCommonResponse() {
        return commonResponse;
    }
}
