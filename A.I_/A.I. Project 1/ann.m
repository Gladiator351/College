y=[17.33191 0.77157 0.20897 0.11402 0.07702 0.05768 0.04589 0.03800 0.03236 0.02814 0.02487 0.02226 0.02013 0.01837 0.01688 0.01562 0.01452 0.01356 0.01272 0.01198 0.01132 0.01072 0.01018 ]
x=[0:1000:22366]
plot(x,y)
grid()
title('Sum-squared network error without momentum constant')
xlabel('Epoch')
ylabel('Sum-squared error')
legend('Activation functon: sigmoid')
