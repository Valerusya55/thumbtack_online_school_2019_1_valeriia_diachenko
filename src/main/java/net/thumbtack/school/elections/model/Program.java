package net.thumbtack.school.elections.model;

import java.util.Collection;
import java.util.List;

public class Program {
    private List<Proposal> program;

    public Program(List<Proposal> program) {
        this.program = program;
    }

    public List<Proposal> getProgram() {
        return program;
    }

    public void setProgram(List<Proposal> program) {
        this.program = program;
    }

    public void addProposal(Proposal proposal) {
        this.program.add(proposal);
    }

    public void deleteProposal(Proposal proposal) {
        program.remove(proposal);
    }

}
