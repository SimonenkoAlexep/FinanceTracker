package com.lsimanenka.financetracker.di

import androidx.lifecycle.ViewModel
import com.lsimanenka.financetracker.domain.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindingModule {

    @Binds @IntoMap @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainVM(vm: MainActivityViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(AccountEditViewModel::class)
    abstract fun bindAccountEditVM(vm: AccountEditViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(ExpensesViewModel::class)
    abstract fun bindExpensesVM(vm: ExpensesViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountVM(vm: AccountViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesVM(vm: CategoriesViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryVM(vm: HistoryViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(TransactionViewModel::class)
    abstract fun bindTransactionVM(vm: TransactionViewModel): ViewModel

}
