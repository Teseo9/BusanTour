package blog.naver.com.hmetal7.busantour.di

import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun busanTourRepository(
        busanTourRepository: BusanTourRepository
    ): BusanTourRepositoryInterface

}