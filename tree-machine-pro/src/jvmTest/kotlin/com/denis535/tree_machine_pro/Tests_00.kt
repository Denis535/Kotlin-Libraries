package com.denis535.tree_machine_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

typealias TreeMachine2 = TreeMachine<Any?, String>
typealias Node2 = Node<Any?, String>

public class Tests_00 {

    @Test
    fun Test_00() {
        TreeMachine2(null).use { machine ->
            // machine.SetRoot root
            machine.SetRoot(Node2("root"), null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 0)
            assertEquals(machine.Root!!.Descendants.count(), 0)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 1)

            // machine.Root.AddChildren a, b
            (machine.Root as Node2).AddChildren(arrayOf(Node("a"), Node("b")), null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 2)
            assertEquals(machine.Root!!.Descendants.count(), 2)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 3)
            for (child in machine.Root!!.Children) {
                assertEquals(child.Owner, machine.Root)
                assertEquals(child.Machine, machine)
                assertEquals(child.IsRoot, false)
                assertEquals(child.Root, machine.Root)
                assertEquals(child.Parent, machine.Root)
                assertEquals(child.Ancestors.count(), 1)
                assertEquals(child.AncestorsAndSelf.count(), 2)
                assertEquals(child.Activity, Activity.Active)
                assertEquals(child.Children.count(), 0)
                assertEquals(child.Descendants.count(), 0)
                assertEquals(child.DescendantsAndSelf.count(), 1)
            }

            // machine.Root.RemoveChildren a, b
            (machine.Root as Node2).RemoveChildren({ true }, null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 0)
            assertEquals(machine.Root!!.Descendants.count(), 0)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 1)

            // machine.SetRoot null
            machine.SetRoot(null, null, null)
            assertEquals(machine.Root, null)
        }
    }

    @Test
    fun Test_01() {
        TreeMachine2(null).apply {
            this.OnCloseCallback = {
                this.SetRoot(null, null, null)
            }
        }.use { machine ->
            // machine.SetRoot root
            machine.SetRoot(Node2("root").apply {
                this.OnCloseCallback = {
                    this.RemoveChildren({ true }, null, null)
                }
            }, null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 0)
            assertEquals(machine.Root!!.Descendants.count(), 0)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 1)

            // machine.Root.AddChildren a, b
            (machine.Root as Node2).AddChildren(arrayOf(Node("a"), Node("b")), null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 2)
            assertEquals(machine.Root!!.Descendants.count(), 2)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 3)
            for (child in machine.Root!!.Children) {
                assertEquals(child.Owner, machine.Root)
                assertEquals(child.Machine, machine)
                assertEquals(child.IsRoot, false)
                assertEquals(child.Root, machine.Root)
                assertEquals(child.Parent, machine.Root)
                assertEquals(child.Ancestors.count(), 1)
                assertEquals(child.AncestorsAndSelf.count(), 2)
                assertEquals(child.Activity, Activity.Active)
                assertEquals(child.Children.count(), 0)
                assertEquals(child.Descendants.count(), 0)
                assertEquals(child.DescendantsAndSelf.count(), 1)
            }
        }
    }

    @Test
    fun Test_02() {
        TreeMachine2(null).apply {
            this.OnCloseCallback = {
                this.Root!!.close()
            }
        }.use { machine ->
            // machine.SetRoot root
            machine.SetRoot(Node2("root").apply {
                this.OnCloseCallback = {
                    for (child in this.Children.reversed()) {
                        child.close()
                    }
                }
            }, null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 0)
            assertEquals(machine.Root!!.Descendants.count(), 0)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 1)

            // machine.Root.AddChildren a, b
            (machine.Root as Node2).AddChildren(arrayOf(Node("a"), Node("b")), null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 2)
            assertEquals(machine.Root!!.Descendants.count(), 2)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 3)
            for (child in machine.Root!!.Children) {
                assertEquals(child.Owner, machine.Root)
                assertEquals(child.Machine, machine)
                assertEquals(child.IsRoot, false)
                assertEquals(child.Root, machine.Root)
                assertEquals(child.Parent, machine.Root)
                assertEquals(child.Ancestors.count(), 1)
                assertEquals(child.AncestorsAndSelf.count(), 2)
                assertEquals(child.Activity, Activity.Active)
                assertEquals(child.Children.count(), 0)
                assertEquals(child.Descendants.count(), 0)
                assertEquals(child.DescendantsAndSelf.count(), 1)
            }
        }
    }

    @Test
    public fun Test_10() {
        TreeMachine2(null).apply {
            this.OnCloseCallback = {
                this.SetRoot(null, null, null)
            }
        }.use { machine ->
            // machine.SetRoot root
            machine.SetRoot(Node2("root").apply {
                this.OnCloseCallback = {
                }
                this.OnAttachCallback = { arg ->
                    this.AddChildren(arrayOf(Node2("a"), Node2("b")), null)
                }
                this.OnDetachCallback = { arg ->
                    this.RemoveChildren({ true }, null, null)
                }
            }, null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 2)
            assertEquals(machine.Root!!.Descendants.count(), 2)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 3)
            for (child in machine.Root!!.Children) {
                assertEquals(child.Owner, machine.Root)
                assertEquals(child.Machine, machine)
                assertEquals(child.IsRoot, false)
                assertEquals(child.Root, machine.Root)
                assertEquals(child.Parent, machine.Root)
                assertEquals(child.Ancestors.count(), 1)
                assertEquals(child.AncestorsAndSelf.count(), 2)
                assertEquals(child.Activity, Activity.Active)
                assertEquals(child.Children.count(), 0)
                assertEquals(child.Descendants.count(), 0)
                assertEquals(child.DescendantsAndSelf.count(), 1)
            }
        }
    }

    @Test
    public fun Test_20() {
        TreeMachine2(null).apply {
            this.OnCloseCallback = {
                this.SetRoot(null, null, null)
            }
        }.use { machine ->
            // machine.SetRoot root
            machine.SetRoot(Node2("root").apply {
                this.OnCloseCallback = {
                }
                this.OnActivateCallback = { arg ->
                    this.AddChildren(arrayOf(Node2("a"), Node2("b")), null)
                }
                this.OnDeactivateCallback = { arg ->
                    this.RemoveChildren({ true }, null, null)
                }
            }, null, null)
            assertNotEquals(machine.Root, null)
            assertEquals(machine.Root!!.Owner, machine)
            assertEquals(machine.Root!!.Machine, machine)
            assertEquals(machine.Root!!.IsRoot, true)
            assertEquals(machine.Root!!.Root, machine.Root)
            assertEquals(machine.Root!!.Parent, null)
            assertEquals(machine.Root!!.Ancestors.count(), 0)
            assertEquals(machine.Root!!.AncestorsAndSelf.count(), 1)
            assertEquals(machine.Root!!.Activity, Activity.Active)
            assertEquals(machine.Root!!.Children.count(), 2)
            assertEquals(machine.Root!!.Descendants.count(), 2)
            assertEquals(machine.Root!!.DescendantsAndSelf.count(), 3)
            for (child in machine.Root!!.Children) {
                assertEquals(child.Owner, machine.Root)
                assertEquals(child.Machine, machine)
                assertEquals(child.IsRoot, false)
                assertEquals(child.Root, machine.Root)
                assertEquals(child.Parent, machine.Root)
                assertEquals(child.Ancestors.count(), 1)
                assertEquals(child.AncestorsAndSelf.count(), 2)
                assertEquals(child.Activity, Activity.Active)
                assertEquals(child.Children.count(), 0)
                assertEquals(child.Descendants.count(), 0)
                assertEquals(child.DescendantsAndSelf.count(), 1)
            }
        }
    }

}
