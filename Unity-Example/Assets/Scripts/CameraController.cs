///////////////////////////////////////////////////////////////////////////////
//
// RealWear Development Software, Source Code and Object Code
// (c) RealWear, Inc. All rights reserved.
//
// Contact info@realwear.com for further information about the use of this
// code.
//
///////////////////////////////////////////////////////////////////////////////

using System;
using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// Class for managing the camera
/// </summary>
public class CameraController : MonoBehaviour
{   
    /// <summary>
    /// The webcam texture that manages the devices camera
    /// </summary>
    [NonSerialized]
    public WebCamTexture Webcam;

    private AspectRatioFitter m_cameraAspectRatio = null;
    private RawImage m_rawImage = null;

    /// <summary>
    /// See Unity docs
    /// </summary>
    void Awake()
    {
        m_cameraAspectRatio = GetComponent<AspectRatioFitter>();
        m_rawImage = GetComponent<RawImage>();

        Webcam = new WebCamTexture();
        m_rawImage.material.mainTexture = Webcam;
        Webcam.requestedWidth = 400;
        Webcam.requestedHeight = 224;

        Webcam.Play();
    }

    /// <summary>
    /// See Unity docs
    /// </summary>
    private void Update()
    {
        float aspectRatio = (float)Webcam.width / (float)Webcam.height;
        m_cameraAspectRatio.aspectRatio = aspectRatio;

        if (Input.deviceOrientation == DeviceOrientation.LandscapeRight)
        {
            m_rawImage.transform.localRotation = new Quaternion(0, 0, 180, 0);
        }
        else
        {
            m_rawImage.transform.localRotation = new Quaternion(0, 0, 0, 0);
        }
    }
}
