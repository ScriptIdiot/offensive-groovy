def proc = "dir c:\\".execute();

def os = new StringBuffer();

proc.waitForProcessOutput(os, System.err);

println(os.toString());