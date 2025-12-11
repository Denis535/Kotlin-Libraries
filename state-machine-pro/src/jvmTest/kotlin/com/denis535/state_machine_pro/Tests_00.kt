package com.denis535.state_machine_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

public class Tests_00 {

    @Test
    fun Test_00() {
        StateMachine().use { machine ->
            // machine.SetRoot a
            machine.SetRoot(State("a"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // machine.SetRoot b
            machine.SetRoot(ChildrenableState("b"), null, null)
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
        StateMachine().use { machine ->
            // machine.SetRoot a
            machine.SetRoot(State("a"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // machine.SetRoot b
            machine.SetRoot(ChildrenableState("b"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // machine.Root.close
            machine.Root!!.close()
            assertEquals(machine.Root!!.IsClosed, true)
        }
    }

}
