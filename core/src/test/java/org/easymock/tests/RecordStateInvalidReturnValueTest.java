/*
 * Copyright 2001-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.easymock.tests;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author OFFIS, Tammo Freese
 */
public class RecordStateInvalidReturnValueTest {

    private IMethods mock;

    @Before
    public void setup() {
        mock = createMock(IMethods.class);
    }

    @Test
    public void setInvalidBooleanReturnValue() {
        try {
            expect((Object) mock.oneArg(false)).andReturn(false);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            assertEquals("incompatible return value type", e.getMessage());
        }

    }

    @Test
    public void setReturnValueForVoidMethod() {
        mock.simpleMethod();
        try {
            expectLastCall().andReturn(null);
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            assertEquals("void method cannot return a value", e.getMessage());
        }
    }

    @Test
    public void nullForPrimitive() {
        try {
            expect(mock.longReturningMethod(4)).andReturn(null);
            fail("null not allowed");
        } catch (IllegalStateException e) {
            assertEquals("can't return null for a method returning a primitive type", e.getMessage());
        }
    }
}
