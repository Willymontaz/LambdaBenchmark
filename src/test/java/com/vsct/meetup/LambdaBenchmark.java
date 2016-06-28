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


@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgs = {"-Xms20m","-Xmx20m"})
@State(Scope.Benchmark)
public class LambdaBenchmark {

    Integer capturable = 2;

    Integer applyFunction(Integer word) {
        return concatenate(word);
    }

    Integer concatenate(Integer word) {
        return word + 2;
    }

    Integer applyLambda(Function<Integer, Integer> lambda, Integer word) {
        return lambda.apply(word);
    }

    Integer applyInner(Inner inner, Integer word){
        return inner.apply(word);
    }

    static interface Inner {
        Integer apply(Integer word);
    }

    /*@Setup
    public void setup(){
        Object.___resetObjectCreationStats___();
    }

    @TearDown
    public void tearDown(){
        Object.___printObjectCreationStats___();
    }*/

    //@Benchmark
    public void baseline() {
    }

    @Benchmark
    public Integer method_call() {
        return applyFunction(1);
    }

    @Benchmark
    public Integer inner_class_without_capture() {
        return applyInner(new Inner() {
            @Override
            public Integer apply(Integer s) {
                return s + 2;
            }
        }, 1);
    }

    @Benchmark
    public Integer lambda_without_capture() {
        return applyLambda(s -> s + 2, 1);
    }

    @Benchmark
    public Integer inner_class_capturing() {
        return applyInner(new Inner() {
            @Override
            public Integer apply(Integer s) {
                return capturable + s;
            }
        }, 1);
    }

    @Benchmark
    public Integer lambda_capturing() {
        return applyLambda(s -> capturable + s, 1);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + LambdaBenchmark.class.getSimpleName() + ".*")
                .build();

        new Runner(opt).run();
    }

}
