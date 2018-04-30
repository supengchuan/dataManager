package ProcessControl.SPC;

import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/2/4 0004.
 */
public class SPC {
    private static final Logger log = Logger.getLogger(SPC.class);

    public XR m_XR(double[][] observation, double Tl, double Tu) {
        log.info("XR图控制被调用");
        XR xr = new XR();
        if (xr.getParameterXR(observation, Tl, Tu)) {
            return xr ;
        } else {
            return null;
        }
    }

    public XS m_XS(double[][] observation, double Tl, double Tu) {
        log.info("XS图控制被调用");
        XS xs = new XS();
        if (xs.getParameterXR(observation, Tl, Tu)) {
            return xs;
        } else {
            return null;
        }
    }

    public P m_P(double[][] observation) {
        log.info("P图控制被调用");
        P p = new P();
        if (p.getParameterP(observation)) {
            return p;
        } else {
            return null;
        }
    }
}
