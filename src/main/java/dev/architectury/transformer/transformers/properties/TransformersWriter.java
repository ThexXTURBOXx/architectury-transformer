/*
 * This file is licensed under the MIT License, part of architectury-transformer.
 * Copyright (c) 2020, 2021 architectury
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.architectury.transformer.transformers.properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.architectury.transformer.Transformer;
import dev.architectury.transformer.util.TransformerEntry;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;

public class TransformersWriter extends Writer {
    private BufferedWriter out;
    
    public TransformersWriter(Writer out) {
        this.out = out instanceof BufferedWriter ? (BufferedWriter) out : new BufferedWriter(out);
    }
    
    public void write(TransformerEntry entry) throws IOException {
        write(entry.getPath(), entry.getTransformer().getClazz(), entry.getTransformer().getProperties());
    }
    
    public void write(Path path, Class<? extends Transformer> clazz) throws IOException {
        write(path, clazz, null);
    }
    
    public void write(Path path, Class<? extends Transformer> clazz, @Nullable JsonObject properties) throws IOException {
        write(path.toString());
        write('|');
        write(clazz.getName());
        if (properties != null && !properties.keySet().isEmpty()) {
            write('|');
            StringWriter writer = new StringWriter();
            new Gson().toJson(properties, writer);
            write(writer.toString().replace(File.pathSeparator, "\\" + File.pathSeparatorChar));
        }
        write(File.pathSeparatorChar);
    }
    
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        out.write(cbuf, off, len);
    }
    
    @Override
    public void flush() throws IOException {
        out.flush();
    }
    
    @Override
    public void close() throws IOException {
        out.close();
    }
}
