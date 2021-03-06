package org.infinispan.stream.impl.intops.object;

import java.util.function.Function;
import java.util.stream.Stream;

import org.infinispan.factories.ComponentRegistry;
import org.infinispan.stream.impl.intops.MappingOperation;
import org.infinispan.util.function.SerializableFunction;

import io.reactivex.Flowable;

/**
 * Performs map to operation on a regular {@link Stream}
 * @param <I> the type of the input stream
 * @param <O> the type of the output stream
 */
public class MapOperation<I, O> implements MappingOperation<I, Stream<I>, O, Stream<O>> {
   private final Function<? super I, ? extends O> function;

   public MapOperation(Function<? super I, ? extends O> function) {
      this.function = function;
   }

   public MapOperation(SerializableFunction<? super I, ? extends O> function) {
      this((Function<? super I, ? extends O>) function);
   }

   @Override
   public Stream<O> perform(Stream<I> stream) {
      return stream.map(function);
   }

   @Override
   public void handleInjection(ComponentRegistry registry) {
      registry.wireDependencies(function);
   }

   public Function<? super I, ? extends O> getFunction() {
      return function;
   }

   @Override
   public Flowable<O> mapFlowable(Flowable<I> input) {
      return input.map(function::apply);
   }
}
