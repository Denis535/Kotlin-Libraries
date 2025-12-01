package com.denis535.tree_machine_pro

internal typealias Proc = () -> Unit
internal typealias Proc1<T> = (T) -> Unit
internal typealias Proc2<T1, T2> = (T1, T2) -> Unit
internal typealias Proc3<T1, T2, T3> = (T1, T2, T3) -> Unit
internal typealias Proc4<T1, T2, T3, T4> = (T1, T2, T3, T4) -> Unit
internal typealias Proc5<T1, T2, T3, T4, T5> = (T1, T2, T3, T4, T5) -> Unit
internal typealias Proc6<T1, T2, T3, T4, T5, T6> = (T1, T2, T3, T4, T5, T6) -> Unit
internal typealias Proc7<T1, T2, T3, T4, T5, T6, T7> = (T1, T2, T3, T4, T5, T6, T7) -> Unit
internal typealias Proc8<T1, T2, T3, T4, T5, T6, T7, T8> = (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit
internal typealias Proc9<T1, T2, T3, T4, T5, T6, T7, T8, T9> = (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit
internal typealias Proc10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit
internal typealias Proc11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit
internal typealias Proc12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit
internal typealias Proc13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> Unit
internal typealias Proc14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> Unit
internal typealias Proc15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> Unit
internal typealias Proc16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> Unit

internal typealias Func<TResult> = () -> TResult
internal typealias Func1<T, TResult> = (T) -> TResult
internal typealias Func2<T1, T2, TResult> = (T1, T2) -> TResult
internal typealias Func3<T1, T2, T3, TResult> = (T1, T2, T3) -> TResult
internal typealias Func4<T1, T2, T3, T4, TResult> = (T1, T2, T3, T4) -> TResult
internal typealias Func5<T1, T2, T3, T4, T5, TResult> = (T1, T2, T3, T4, T5) -> TResult
internal typealias Func6<T1, T2, T3, T4, T5, T6, TResult> = (T1, T2, T3, T4, T5, T6) -> TResult
internal typealias Func7<T1, T2, T3, T4, T5, T6, T7, TResult> = (T1, T2, T3, T4, T5, T6, T7) -> TResult
internal typealias Func8<T1, T2, T3, T4, T5, T6, T7, T8, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8) -> TResult
internal typealias Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> TResult
internal typealias Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> TResult
internal typealias Func11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> TResult
internal typealias Func12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> TResult
internal typealias Func13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> TResult
internal typealias Func14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> TResult
internal typealias Func15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> TResult
internal typealias Func16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, TResult> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> TResult

internal typealias Predicate = () -> Boolean
internal typealias Predicate1<T1> = (T1) -> Boolean
internal typealias Predicate2<T1, T2> = (T1, T2) -> Boolean
internal typealias Predicate3<T1, T2, T3> = (T1, T2, T3) -> Boolean
internal typealias Predicate4<T1, T2, T3, T4> = (T1, T2, T3, T4) -> Boolean
internal typealias Predicate5<T1, T2, T3, T4, T5> = (T1, T2, T3, T4, T5) -> Boolean
internal typealias Predicate6<T1, T2, T3, T4, T5, T6> = (T1, T2, T3, T4, T5, T6) -> Boolean
internal typealias Predicate7<T1, T2, T3, T4, T5, T6, T7> = (T1, T2, T3, T4, T5, T6, T7) -> Boolean
internal typealias Predicate8<T1, T2, T3, T4, T5, T6, T7, T8> = (T1, T2, T3, T4, T5, T6, T7, T8) -> Boolean
internal typealias Predicate9<T1, T2, T3, T4, T5, T6, T7, T8, T9> = (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Boolean
internal typealias Predicate10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Boolean
internal typealias Predicate11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Boolean
internal typealias Predicate12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Boolean
internal typealias Predicate13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> Boolean
internal typealias Predicate14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> Boolean
internal typealias Predicate15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> Boolean
internal typealias Predicate16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> = (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> Boolean

internal typealias ELifecycle = Lifecycle
internal typealias EActivity = Activity
