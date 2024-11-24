package org.example;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.FileOutputStream;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class BytecodeGenerator {
    private final Map<String, Integer> variableIndexMap = new HashMap<>();
    private int nextVarIndex = 1; // Local variable index (0 is for "args")

    public void generate(List<ASTNode> ast, String className) throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        MethodVisitor mv;

        // Define class header
        cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);

        // Generate main method
        mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // Process each AST node
        for (ASTNode node : ast) {
            if (node instanceof LetStatement letStmt) {
                // Store variable in a local slot
                mv.visitLdcInsn(letStmt.value);          // Push integer value
                mv.visitVarInsn(ISTORE, nextVarIndex);   // Store in local variable
                variableIndexMap.put(letStmt.variable, nextVarIndex++);
            } else if (node instanceof ShowStatement showStmt) {
                if (variableIndexMap.containsKey(showStmt.variableOrValue)) {
                    // Load variable and print
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitVarInsn(ILOAD, variableIndexMap.get(showStmt.variableOrValue));
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                } else {
                    // Print constant integer
                    int value = Integer.parseInt(showStmt.variableOrValue);
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn(value);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                }
            }
        }

        // Return from main method
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        // Finalize class
        cw.visitEnd();

        // Write class file
        FileOutputStream fos = new FileOutputStream(className + ".class");
        fos.write(cw.toByteArray());
        fos.close();

        System.out.println("Compiled " + className + ".class successfully!");
    }
}