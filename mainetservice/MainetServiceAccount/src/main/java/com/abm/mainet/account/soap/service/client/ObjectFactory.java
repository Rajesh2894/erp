
package com.abm.mainet.account.soap.service.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.abm.mainet.account.soap.service.client package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java
 * representation of XML content can consist of schema derived interfaces and classes representing the binding of schema type
 * definitions, element declarations and model groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Exception_QNAME = new QName("http://service.soap.account.mainet.abm.com/", "Exception");
    private final static QName _DoVoucherPosting_QNAME = new QName("http://service.soap.account.mainet.abm.com/",
            "doVoucherPosting");
    private final static QName _DoVoucherPostingResponse_QNAME = new QName("http://service.soap.account.mainet.abm.com/",
            "doVoucherPostingResponse");
    private final static QName _DoVoucherReversePosting_QNAME = new QName("http://service.soap.account.mainet.abm.com/",
            "doVoucherReversePosting");
    private final static QName _DoVoucherReversePostingResponse_QNAME = new QName("http://service.soap.account.mainet.abm.com/",
            "doVoucherReversePostingResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * com.abm.mainet.account.soap.service.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link DoVoucherPosting }
     * 
     */
    public DoVoucherPosting createDoVoucherPosting() {
        return new DoVoucherPosting();
    }

    /**
     * Create an instance of {@link DoVoucherPostingResponse }
     * 
     */
    public DoVoucherPostingResponse createDoVoucherPostingResponse() {
        return new DoVoucherPostingResponse();
    }

    /**
     * Create an instance of {@link DoVoucherReversePosting }
     * 
     */
    public DoVoucherReversePosting createDoVoucherReversePosting() {
        return new DoVoucherReversePosting();
    }

    /**
     * Create an instance of {@link DoVoucherReversePostingResponse }
     * 
     */
    public DoVoucherReversePostingResponse createDoVoucherReversePostingResponse() {
        return new DoVoucherReversePostingResponse();
    }

    /**
     * Create an instance of {@link VoucherPostDTO }
     * 
     */
    public VoucherPostDTO createVoucherPostDTO() {
        return new VoucherPostDTO();
    }

    /**
     * Create an instance of {@link VoucherPostDetailDTO }
     * 
     */
    public VoucherPostDetailDTO createVoucherPostDetailDTO() {
        return new VoucherPostDetailDTO();
    }

    /**
     * Create an instance of {@link VoucherReversePostDTO }
     * 
     */
    public VoucherReversePostDTO createVoucherReversePostDTO() {
        return new VoucherReversePostDTO();
    }

    /**
     * Create an instance of {@link VoucherReversePostDetailDTO }
     * 
     */
    public VoucherReversePostDetailDTO createVoucherReversePostDetailDTO() {
        return new VoucherReversePostDetailDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.account.mainet.abm.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoVoucherPosting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.account.mainet.abm.com/", name = "doVoucherPosting")
    public JAXBElement<DoVoucherPosting> createDoVoucherPosting(DoVoucherPosting value) {
        return new JAXBElement<DoVoucherPosting>(_DoVoucherPosting_QNAME, DoVoucherPosting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoVoucherPostingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.account.mainet.abm.com/", name = "doVoucherPostingResponse")
    public JAXBElement<DoVoucherPostingResponse> createDoVoucherPostingResponse(DoVoucherPostingResponse value) {
        return new JAXBElement<DoVoucherPostingResponse>(_DoVoucherPostingResponse_QNAME, DoVoucherPostingResponse.class, null,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoVoucherReversePosting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.account.mainet.abm.com/", name = "doVoucherReversePosting")
    public JAXBElement<DoVoucherReversePosting> createDoVoucherReversePosting(DoVoucherReversePosting value) {
        return new JAXBElement<DoVoucherReversePosting>(_DoVoucherReversePosting_QNAME, DoVoucherReversePosting.class, null,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoVoucherReversePostingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.account.mainet.abm.com/", name = "doVoucherReversePostingResponse")
    public JAXBElement<DoVoucherReversePostingResponse> createDoVoucherReversePostingResponse(
            DoVoucherReversePostingResponse value) {
        return new JAXBElement<DoVoucherReversePostingResponse>(_DoVoucherReversePostingResponse_QNAME,
                DoVoucherReversePostingResponse.class, null, value);
    }

}
