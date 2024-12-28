package de.rogallab.mobile

import de.rogallab.mobile.data.IDataStore
import de.rogallab.mobile.data.local.datastore.DataStore
import de.rogallab.mobile.data.repositories.PersonRepository
import de.rogallab.mobile.domain.IPersonRepository
import de.rogallab.mobile.domain.utilities.logInfo
import de.rogallab.mobile.ui.IErrorHandler
import de.rogallab.mobile.ui.INavigationHandler
import de.rogallab.mobile.ui.errors.ErrorHandler
import de.rogallab.mobile.ui.navigation.NavigationHandler
import de.rogallab.mobile.ui.people.PersonViewModel
import de.rogallab.mobile.ui.people.PersonValidator
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModules: Module = module {
   val tag = "<-uiModules"

   // Provide PersonViewModel --------------------------------------------
   logInfo(tag, "factory   -> PeopleInputValidator")
   factory<PersonValidator> {
      PersonValidator(
         _context = androidContext()
      )
   }

   logInfo(tag, "viewModel -> PersonViewModel")
   viewModel<PersonViewModel> {
      PersonViewModel(
         _repository = get<IPersonRepository>(),
         _validator = get<PersonValidator>(),
         _navigationHandler = get<INavigationHandler>(),
         _errorHandler = get<IErrorHandler>())
   }
}

val domainModules: Module = module {
   val tag = "<-domainModules"
   logInfo(tag, "nothing do do")
}

val dataModules = module {
   val tag = "<-dataModules"

   logInfo(tag, "factory   -> INavigationHandler: NavigationHandler")
   factory<INavigationHandler> {
      NavigationHandler()
   }

   logInfo(tag, "factory   -> IErrorHandler: ErrorHandler")
   factory<IErrorHandler> {
      ErrorHandler()
   }

   logInfo(tag, "single    -> DataStore: IDataStore")
   single<IDataStore> {
      DataStore(
         _context = androidContext()
      )
   }

   logInfo(tag, "single    -> PersonRepository: IPersonRepository")
   single<IPersonRepository> {
      PersonRepository(
         _dataStore = get<IDataStore>()
      )
   }
}
