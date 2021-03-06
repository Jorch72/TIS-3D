package li.cil.tis3d.common.module.execution.instruction;

import li.cil.tis3d.api.machine.HaltAndCatchFireException;
import li.cil.tis3d.common.module.execution.Machine;

public final class InstructionHaltAndCatchFire implements Instruction {
    public static final Instruction INSTANCE = new InstructionHaltAndCatchFire();

    @Override
    public void step(final Machine machine) {
        throw new HaltAndCatchFireException();
    }

    @Override
    public String toString() {
        return "HCF";
    }
}
