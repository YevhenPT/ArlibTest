package com.sts.investpuzzle

import com.sts.investpuzzle.utils.rx.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val mTestschduler: TestScheduler): SchedulerProvider {
    override fun ui(): Scheduler {
       return mTestschduler;
    }

    override fun computation(): Scheduler {
        return mTestschduler;
    }

    override fun io(): Scheduler {
        return mTestschduler;
    }
}