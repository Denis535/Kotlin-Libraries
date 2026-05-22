package com.denis535.state_machine_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

public class Tests_00 {

    @Test
    fun Test_00() {
        StateMachine<AbstractState2>().use { machine ->
            // machine.SetRoot State
            machine.SetRoot(State2(), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)

            // machine.SetRoot ChildrenableState
            machine.SetRoot(ChildrenableState2(), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)

            // machine.SetRoot null
            machine.SetRoot(null, null, null)
            assertEquals(machine.Root, null)
        }
    }

    @Test
    fun Test_01() {
        StateMachine<AbstractState2>().use { machine ->
            // machine.SetRoot State
            machine.SetRoot(State2(), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)

            // machine.SetRoot ChildrenableState
            machine.SetRoot(ChildrenableState2(), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)

            // machine.Root.close
            machine.Root!!.close()
            assertEquals(machine.Root!!.IsClosed, true)
        }
    }

}

internal interface AbstractState2 : AbstractState<AbstractState2> {}

internal class State2 : State<AbstractState2>, AbstractState2 {
    public constructor() : super()
}

internal class ChildrenableState2 : ChildrenableState<AbstractState2>, AbstractState2 {
    public constructor() : super()
}
