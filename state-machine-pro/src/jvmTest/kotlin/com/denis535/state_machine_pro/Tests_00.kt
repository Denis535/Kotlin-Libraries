package com.denis535.state_machine_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

public class Tests_00 {

    @Test
    fun Test_00() {
        StateMachine().use { machine ->
            // SetState a
            machine.SetRoot(State("a"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // SetState b
            machine.SetRoot(ChildrenableState("b"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // SetState null
            machine.SetRoot(null, null, null)
            assertEquals(machine.Root, null)
        }
    }

    @Test
    fun Test_01() {
        StateMachine().apply {
            this.OnCloseCallback = {
                this.SetRoot(null, null, null)
            }
        }.use { machine ->
            // SetState a
            machine.SetRoot(State("a"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // SetState b
            machine.SetRoot(ChildrenableState("b"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
        }
    }

    @Test
    fun Test_02() {
        StateMachine().apply {
            this.OnCloseCallback = {
                this.Root!!.close()
            }
        }.use { machine ->
            // SetState a
            machine.SetRoot(State("a"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            // SetState b
            machine.SetRoot(ChildrenableState("b"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.Activity, Activity.Active)
        }
    }

}
