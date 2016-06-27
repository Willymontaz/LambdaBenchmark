package com.vsct.meetup;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.CompilerProfiler;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * ~  Copyright (C) 2016 VSCT
 * ~
 * ~  Licensed under the Apache License, Version 2.0 (the "License");
 * ~  you may not use this file except in compliance with the License.
 * ~  You may obtain a copy of the License at
 * ~
 * ~   http://www.apache.org/licenses/LICENSE-2.0
 * ~
 * ~  Unless required by applicable law or agreed to in writing, software
 * ~  distributed under the License is distributed on an "AS IS" BASIS,
 * ~  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * ~  See the License for the specific language governing permissions and
 * ~  limitations under the License.
 * ~
 */


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgs = {"-Xms256m","-Xmx256m"})
@State(Scope.Benchmark)
public class LambdaBenchmark {

    String capturable = "concat";

    String applyFunction(String word) {
        return concatenate(word);
    }

    String concatenate(String word) {
        return word + " concat";
    }

    String applyLambda(Function<String, String> lambda, String word) {
        return lambda.apply(word);
    }

    String applyInner(Inner inner, String word){
        return inner.apply(word);
    }

    static interface Inner {
        String apply(String word);
    }

    @Benchmark
    public void baseline() {
    }

    @Benchmark
    public String method_call() {
        return applyFunction("word");
    }

    @Benchmark
    public String inner_class_without_capture() {
        return applyInner(new Inner() {
            @Override
            public String apply(String s) {
                return s + "concat";
            }
        }, "word");
    }

    @Benchmark
    public String lambda_without_capture() {
        return applyLambda(s -> {
            return s + "concat";
        }, "word");
    }

    @Benchmark
    public String inner_class_capturing() {
        return applyInner(new Inner() {
            @Override
            public String apply(String s) {
                return s + capturable;
            }
        }, "word");
    }

    @Benchmark
    public String lambda_capturing() {
        return applyLambda(s -> {
            return s + capturable;
        }, "word");
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + LambdaBenchmark.class.getSimpleName() + ".*")
                .addProfiler(GCProfiler.class)
                .build();

        new Runner(opt).run();
    }

}
