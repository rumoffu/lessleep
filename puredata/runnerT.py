import os, subprocess
import sys
import time
cmd = 'cp ~/vboxshared/class/spring2014git/myml/homework/bin/cs475/* ./cs475'
print cmd
subprocess.call(cmd, shell = True)

#datasets = ['easy', 'hard', 'bio', 'finance', 'speech', 'vision', 'circle'] #no nlp
datasets = ['easy', 'hard', 'bio', 'finance', 'speech', 'vision', 'circle'] #no nlp
#datasets = ['easy', 'vision', 'finance', 'vision', 'circle', 'hard', 'speech'] #no nlp
#algorithms = ['margin_perceptron', 'perceptron_linear_kernel', 'perceptron_polynomial_kernel', 'mira']
algorithms = ['mira']
models = []
all_time = time.time()
for a in algorithms:
    print "Results for {a} algorithm:\n".format(**locals())
    for d in datasets:
        start_time = time.time()
        print 'Accuracy on {d} dataset:'.format(**locals())
        cmd = 'java -cp library.jar:commons-cli-1.2.jar:. cs475.Classify -mode train -algorithm {0} -model_file {1}.model -data {2}'. format(a, d + a, d+'.train')
        #print cmd
        subprocess.call(cmd, shell = True)
        models.append((d + '.model', d))
        #print
        mid_time = time.time()
        print mid_time - start_time, "seconds"
        cmd = 'java -cp library.jar:commons-cli-1.2.jar:. cs475.Classify -mode test -model_file {0} -data {1}.dev -predictions_file {0}.dev.predictions'.format(d+a + '.model', d)
        #print cmd
        subprocess.call(cmd, shell = True)
        print time.time() - mid_time, "seconds"
        print
    print 
print "Total: ", time.time() - all_time, "seconds"
'''
cmd = 'rm *.model'
print cmd
subprocess.call(cmd, shell = True)

cmd = 'rm *.predictions'
print cmd
subprocess.call(cmd, shell = True)
'''
