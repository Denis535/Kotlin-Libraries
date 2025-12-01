package com.denis535.state_machine_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

typealias StateMachine2 = StateMachine<Any?, String>
typealias State2 = State<Any?, String>

public class Tests_00 {

    @Test
    fun Test_00() {
        StateMachine2(null).use { machine ->
            // SetState a
            machine.SetRoot(State2("a"), null, null)
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
        StateMachine2(null).apply {
            this.OnCloseCallback = {
                this.SetRoot(null, null, null)
            }
        }.use { machine ->
            // SetState a
            machine.SetRoot(State2("a"), null, null)
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
        StateMachine2(null).apply {
            this.OnCloseCallback = {
                this.Root!!.close()
            }
        }.use { machine ->
            // SetState a
            machine.SetRoot(State2("a"), null, null)
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
