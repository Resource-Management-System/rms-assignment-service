package callum.project.uni.rms.assignment.mapper;

public interface Mapper<T,S,R> {

    T mapSourceToTarget(S source);
    
    S mapTargetToSource(T target);
    
    S mapRequestToSource(R request);
}
