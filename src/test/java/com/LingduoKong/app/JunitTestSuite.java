package com.LingduoKong.app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by lingduokong on 2/21/16.
 */

@RunWith(value=Suite.class)
@Suite.SuiteClasses(value={
        RecommendQueryTest.class, SearchQueryTest.class, ReviewQueryTest.class
})
public class JunitTestSuite {

}
