/**
 * 
 */
package commands.god

import java.lang.management.*
def stream = source.getTerminalOutput()

def os = ManagementFactory.operatingSystemMXBean
stream.writeln """OPERATING SYSTEM:
\tarchitecture = $os.arch
\tname = $os.name
\tversion = $os.version
\tprocessors = $os.availableProcessors
"""

def rt = ManagementFactory.runtimeMXBean
stream.writeln """RUNTIME:
\tname = $rt.name
\tspec name = $rt.specName
\tvendor = $rt.specVendor
\tspec version = $rt.specVersion
\tmanagement spec version = $rt.managementSpecVersion
"""

def cl = ManagementFactory.classLoadingMXBean
stream.writeln """CLASS LOADING SYSTEM:
\tisVerbose = ${cl.isVerbose()}
\tloadedClassCount = $cl.loadedClassCount
\ttotalLoadedClassCount = $cl.totalLoadedClassCount
\tunloadedClassCount = $cl.unloadedClassCount
"""

def comp = ManagementFactory.compilationMXBean
stream.writeln """COMPILATION:
\ttotalCompilationTime = $comp.totalCompilationTime
"""

def mem = ManagementFactory.memoryMXBean
def heapUsage = mem.heapMemoryUsage
def nonHeapUsage = mem.nonHeapMemoryUsage
stream.writeln """MEMORY:
HEAP STORAGE:
\tcommitted = $heapUsage.committed
\tinit = $heapUsage.init
\tmax = $heapUsage.max
\tused = $heapUsage.used
NON-HEAP STORAGE:
\tcommitted = $nonHeapUsage.committed
\tinit = $nonHeapUsage.init
\tmax = $nonHeapUsage.max
\tused = $nonHeapUsage.used
"""

ManagementFactory.memoryPoolMXBeans.each{ mp ->
    stream.writeln "\tname: " + mp.name
    String[] mmnames = mp.memoryManagerNames
    mmnames.each{ mmname ->
    stream.writeln "\t\tManager Name: $mmname"
    }
    stream.writeln "\t\tmtype = $mp.type"
    stream.writeln "\t\tUsage threshold supported = " + mp.isUsageThresholdSupported()
}
stream.writeln()

def td = ManagementFactory.threadMXBean
stream.writeln "THREADS:"
td.allThreadIds.each { tid ->
stream.writeln "\tThread name = ${td.getThreadInfo(tid).threadName}"
}
stream.writeln()

stream.writeln "GARBAGE COLLECTION:"
ManagementFactory.garbageCollectorMXBeans.each { gc ->
stream.writeln "\tname = $gc.name"
stream.writeln "\t\tcollection count = $gc.collectionCount"
stream.writeln "\t\tcollection time = $gc.collectionTime"
    String[] mpoolNames = gc.memoryPoolNames
    mpoolNames.each { mpoolName ->
    stream.writeln "\t\tmpool name = $mpoolName"
    }
}